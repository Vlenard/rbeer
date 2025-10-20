import express from 'express';

const router = express.Router();

router.get("/", (req, res) => {
  res.render("index", { 
    lang: "hu",
    title: "Rbeer"
  });
});

router.get("/login", (req, res) => {
  res.render("./auth/login", {
    lang: "hu",
    title: "Bejelentkezés",
    url: req.protocol + '://' + req.get('host')});
});

router.get("/registration", (req, res) => {
  res.render("./auth/registration", {
    lang: "hu",
    title: "Regisztráció", 
    url: req.protocol + '://' + req.get('host')});
});

router.get("/failed_to_registrate", (req, res) => {
  res.render("./auth/failed_to_registrate", {
    lang: "hu",
    title: "Sikertelen regisztráció", 
    url: req.protocol + '://' + req.get('host')});
});


router.get("/failed_to_login", (req, res) => {
  res.render("./auth/failed_to_login", {
    lang: "hu",
    title: "Sikertelen belépés", 
    url: req.protocol + '://' + req.get('host')});
});


export default router;