import { NextFunction } from "express";

const authorize = (req: Request, res: Response, next: NextFunction): void => {
    next();
};

export default authorize;