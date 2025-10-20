import express from 'express';
import authViews from './views/authViews';

const router = express.Router();

router.use(authViews);

router.get("/*app", (req, res) => {
  res.render("app/index", { layout: false, title: "app", lang: "hu" });
});

export default router;