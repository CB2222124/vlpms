import React, {useContext} from "react";
import {UserContext} from "./UserContextComponent";
import {Button} from "react-bootstrap";

/**
 * Sets the user to undefined when clicked provided user context setter is available.
 * @constructor
 */
function LogoutComponent() {

    const userContext = useContext(UserContext);

    function logout() {
        if (userContext?.setUser) {
            userContext.setUser(undefined);
        }
    }

    return (
        <Button onClick={logout}><span><i className="fa fa-user-minus me-2"/>Logout</span></Button>
    );
}

export default LogoutComponent;