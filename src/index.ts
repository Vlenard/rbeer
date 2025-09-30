import express from "express";
//@ts-ignore
import dotenv from 'dotenv';
//@ts-ignore
import cors from "cors";
import viewsRouter from "./routes/views";
import apiRouter from "./routes/api";
import { engine } from "express-handlebars";

dotenv.config();
const env = process.env.NODE_ENV || "development";
const port = process.env.PORT || 3000;
console.log(`Running in ${env} mode`);

const app = express();

app.engine("handlebars", engine());
app.set("trust proxy", true);
app.set("view engine", "handlebars");
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(express.static("public"));
app.use("/api", apiRouter);
app.use("/", viewsRouter);

app.listen(port, () => {
  console.log(`Server is running locally on http://localhost:${port}`);
});