import { Request, Response, NextFunction } from "express";
import bcrypt from "bcryptjs";
import { PrismaClient } from "../prisma/dist/index";
import jwt from "jsonwebtoken";

const prisma = new PrismaClient();

const generateAccessToken = (user: any) => {
  return jwt.sign(
    { id: user.id, email: user.email },
    process.env.AUTH_SECRET as string,
    { expiresIn: "1h" }
  );
};

const setAuthCookie = (res: Response, token: string) => {
  res.cookie("token", token, {
    httpOnly: true,
    secure: process.env.NODE_ENV === "production", // true in production (HTTPS)
    sameSite: "strict",
    maxAge: 60 * 60 * 1000, // 1h
  });
};

const authorize = async (req: Request, res: Response, next: NextFunction) => {
  const token = req.cookies?.token; // read from cookie

  if (!token) return res.sendStatus(401);

  jwt.verify(token, process.env.AUTH_SECRET as string, (err: any, user: any) => {
    if (err) return res.sendStatus(403);
    (req as any).user = user;
    next();
  });
};

const register = async (req: Request, res: Response) => {
  const { email, password, name } = req.body;

  try {
    const existingUser = await prisma.user.findUnique({ where: { email } });
    if (existingUser)
      return res.status(400).json({ error: "Email already exists" });

    const passwordHash = await bcrypt.hash(password, 10);

    const user = await prisma.user.create({
      data: { email, passwordHash },
    });

    const accessToken = generateAccessToken(user);
    setAuthCookie(res, accessToken);

    // Add JWT to header
    res.header("Authorization", `Bearer ${accessToken}`);

    return res.redirect(302, "/app");
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Registration failed" });
  }
};

const login = async (req: Request, res: Response) => {
  const { email, password } = req.body;

  try {
    const user = await prisma.user.findUnique({ where: { email } });
    if (!user)
      return res.status(401).json({ error: "Invalid email or password" });

    const validPassword = await bcrypt.compare(password, user.passwordHash);
    if (!validPassword)
      return res.status(401).json({ error: "Invalid email or password" });

    const accessToken = generateAccessToken(user);
    setAuthCookie(res, accessToken);

    return res.redirect(302, "/app");
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Login failed" });
  }
};

const logout = (req: Request, res: Response) => {
  res.clearCookie("token", {
    httpOnly: true,
    secure: process.env.NODE_ENV === "production",
    sameSite: "strict",
  });
  res.redirect("/login");
};

export default { authorize, register, login, logout };
