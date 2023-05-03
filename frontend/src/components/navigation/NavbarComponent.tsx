import React, {useContext} from "react";
import {Link} from "react-router-dom";
import {UserContext} from "../user/UserContextComponent";
import {Nav, Navbar} from "react-bootstrap";
import LoginComponent from "../user/LoginComponent";
import RegisterComponent from "../user/RegisterComponent";
import LogoutComponent from "../user/LogoutComponent";

function NavbarComponent() {

    const userContext = useContext(UserContext);

    function userNavComponents() {
        if (userContext?.user) return (<LogoutComponent/>)
        return (<div>
            <LoginComponent></LoginComponent>
            <RegisterComponent></RegisterComponent>
        </div>)
    }

    return (
        <Navbar bg="primary" variant="dark">
            <Navbar.Brand as={Link} to={"/"}>
                <i className="fa fa-car me-2 ms-2"/>
                VLPMS
            </Navbar.Brand>
            <Nav className="me-auto">
                <Nav.Link as={Link} to={"/"} active><i className="fa fa-home me-2"/>Home</Nav.Link>
                <Nav.Link as={Link} to={"/search"} active><i className="fa fa-search me-2"/>Search</Nav.Link>
                <Nav.Link as={Link} to={"/registrations"} active><i className="fa fa-box me-2"/>Registrations</Nav.Link>
                <Nav.Link as={Link} to={"/wishlist"} active><i className="fa fa-heart me-2"/>Wishlist</Nav.Link>
            </Nav>
            {userNavComponents()}
        </Navbar>
    );
}

export default NavbarComponent;