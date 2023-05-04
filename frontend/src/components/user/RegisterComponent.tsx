import {Button, Form, Modal} from "react-bootstrap";
import React, {useContext, useState} from "react";
import axios from "axios";
import {UserContext} from "./UserContextComponent";

/**
 * Opens a validated form within a modal that allows the user to register.
 * The backend service is called and if the request is valid, global user context is updated,
 * otherwise an appropriate error message is produced.
 */
function RegisterComponent() {

    const userContext = useContext(UserContext);

    const [show, setShow] = useState(false);
    const open = () => {
        setUsernameInfo({hint: "We will not share your username with anyone.", error: ""});
        setShow(true);
    }
    const close = () => setShow(false);

    const [username, setUsername] = useState<string>("");
    const [usernameInfo, setUsernameInfo] = useState({
        hint: "",
        error: ""
    });
    const handleUsernameChange = (event: any) => {
        setUsernameInfo({hint: "We will not share your username with anyone.", error: ""});
        setUsername(event.target.value);
    }

    /**
     * Calls the backend service and updates user context or sets error messages.
     * @param event Form submit event.
     */
    function register(event: any) {
        event.preventDefault();
        axios.post("http://localhost:8080/customer/register", {
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
                if (error.response.status == 400) {
                    setUsernameInfo({hint: "", error: "Username already in use."});
                } else {
                    setUsernameInfo({hint: "", error: "Server error, please try again."});
                }
            })
    }

    return (
        <>
            <Button onClick={open}><span><i className="fa fa-user-plus me-2"/>Register</span></Button>
            <Modal show={show} onHide={close}>
                <Modal.Header closeButton>
                    <Modal.Title>Register</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={register}>
                        <Form.Group className="mb-3">
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="text" placeholder="Username" onChange={handleUsernameChange} required/>
                            <Form.Text className="text-muted">{usernameInfo.hint}</Form.Text>
                            <Form.Text className="text-danger">{usernameInfo.error}</Form.Text>
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

export default RegisterComponent;