import type { FC } from "react";
import { NavLink } from "react-router";

const Home: FC = () => {
  return (
    <div>
        <p>Home</p>
        <NavLink to={"/app/profile"}>to profile</NavLink>
    </div>
  );
};

export default Home;