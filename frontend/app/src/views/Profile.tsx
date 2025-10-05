import type { FC } from "react";
import { NavLink } from "react-router";

const Profile: FC = () => {
    return (
        <div>
            <p>Profile</p>
            <NavLink to={"/app"}>to home</NavLink>
        </div>
    );
};

export default Profile;