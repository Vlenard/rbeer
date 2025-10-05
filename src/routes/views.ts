import express from 'express';

const router = express.Router();

router.get("/", (req, res) => {
  res.render("index", { layout: false, title: "landing", message: "Welcome to the Home Page!", lang: "hu" });
});

router.get("/login", (req, res) => {
  res.render("./auth/login", { layout: false, title: "Login", message: "Welcome", lang: "hu" });
});

router.get("/registration", (req, res) => {
  res.render("./auth/registration", { layout: false, title: "Registration", message: "Welcome", lang: "hu" });
});

router.get("/*app", (req, res) => {
  res.render("app/index", { layout: false, title: "app", lang: "hu" });
});

export default router;