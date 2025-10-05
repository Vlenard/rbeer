import express from 'express';
import { url } from 'inspector';

const router = express.Router();

router.get("/", (req, res) => {
  res.render("index", { layout: false, title: "landing", message: "Welcome to the Home Page!", lang: "hu" });
});

router.get("/login", (req, res) => {
  res.render("./auth/login", { 
    layout: false, 
    lang: "hu",
    title: "Login",
    url: req.protocol + '://' + req.get('host')});
});

router.get("/registration", (req, res) => {
  res.render("./auth/registration", { 
    layout: false, 
    lang: "hu",
    title: "Registration", 
    url: req.protocol + '://' + req.get('host')});
});

router.get("/fail", (req, res) => {
  res.render("./auth/fail", { 
    layout: false, 
    title: "Registration fail", 
    message: "Something goes wrong try again", 
    lang: "hu",
    url: req.protocol + '://' + req.get('host')});
});

router.get("/*app", (req, res) => {
  res.render("app/index", { layout: false, title: "app", lang: "hu" });
});

export default router;