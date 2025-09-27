import express from "express";
//@ts-ignore
import dotenv from 'dotenv';
//@ts-ignore
import cors from "cors";

dotenv.config();
const env = process.env.NODE_ENV || "development";
console.log(`Running in ${env} mode`);

const app = express();

app.set("trust proxy", true);
app.set("view engine", "pug");
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(express.static("public"));

app.get("/", (req, res) => {
  res.render("index", { title: "landing", message: "Welcome to the Home Page!" });
});

app.get("/app", (req, res) => {
  res.render("app/index", { title: "app"} );
});

app.listen(3000, () => {
  console.log("Server is running locally on http://localhost:3000");
});