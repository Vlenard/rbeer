import type { FC } from "react";
import { Route, Routes } from "react-router";

const App: FC = () => {
  return (
    <Routes>
      <Route path="/app" element={<div>Home</div>} />
    </Routes>
  );
};

export default App;