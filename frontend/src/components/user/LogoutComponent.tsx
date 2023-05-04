import React, {useContext} from "react";
import {UserContext} from "./UserContextComponent";
import {Button} from "react-bootstrap";

/**
 * Button wrapper component used to set the user to undefined when clicked provided user context setter is available.
 */
function LogoutComponent() {

    const userContext = useContext(UserContext);

    /**
     * Sets the user to undefined provided user context setter is available.
     */
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