import express from 'express';

const router = express.Router();

router.get("/", (req, res) => {
  res.render("index", { layout: false, title: "landing", message: "Welcome to the Home Page!" });
});

router.get("/app", (req, res) => {
  res.render("app/index", { layout: false, title: "app" });
});

export default router;