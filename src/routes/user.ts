import express from "express";
import auth from "../auth"

const router = express.Router();

router.post("/login", auth.login);
router.post("/registration", auth.registration);

export default router;
