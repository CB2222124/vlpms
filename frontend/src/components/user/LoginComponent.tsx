import {Button, Form, Modal} from "react-bootstrap";
import React, {useContext, useState} from "react";
import axios from "axios";
import {UserContext} from "./UserContextComponent";

/**
 * Opens a form within a modal that allows the user to enter their username and password.
 * The backend application is called and if the request is valid, global user context is updated,
 * otherwise an error message is produced.
 * @constructor
 */
function LoginComponent() {

    const userContext = useContext(UserContext);

    //Opening and closing the modal.
    const [show, setShow] = useState(false);
    const open = () => {
        setUsernameError("");
        setShow(true);
    }
    const close = () => setShow(false);

    //Updating user input and errors.
    const [username, setUsername] = useState<string>("");
    const [usernameError, setUsernameError] = useState("");
    const handleUsernameChange = (event: any) => {
        setUsernameError("");
        setUsername(event.target.value);
    }

    /**
     * Calls the backend application with the provided username. If it is valid the customer ID will be returned.
     * In practice this should be a session token after authentication.
     * The global user context is updated to store the current
     * @param event
     */
    function login(event: any) {
        event.preventDefault();
        axios.post("http://localhost:8080/customer/login", {
                username: username
            })
            .then(response => {
                const id = response.data as number;
                if (userContext?.setUser) {
                    userContext.setUser({username: username, id: id});
                } else {
                    close();
                }
            })
            .catch(error => {
                if (error.response.status == 404) {
                    setUsernameError("Username does not exist.");
                } else {
                    setUsernameError("Server error, please try again.");
                }
            })
    }

    return (
        <>
            <Button onClick={open}><span><i className="fa fa-user me-2"/>Login</span></Button>
            <Modal show={show} onHide={close}>
                <Modal.Header closeButton>
                    <Modal.Title>Login</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={login}>
                        <Form.Group className="mb-3">
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="text" placeholder="Username" onChange={handleUsernameChange} required/>
                            <Form.Text className="text-danger">{usernameError}</Form.Text>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password"/>
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Submit
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default LoginComponent;