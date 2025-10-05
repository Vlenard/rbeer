import { lazy, type FC } from "react";
import { Route, Routes } from "react-router";
import Home from "./views/Home";

const Profile = lazy(() => import("./views/Profile"));

const App: FC = () => {
  return (
    <Routes>
      <Route path="/app" element={<Home />} />
      <Route path="/app/profile" element={<Profile />} />
      <Route path="*" element={<div>Not Found</div>} />
    </Routes>
  );
};

export default App;