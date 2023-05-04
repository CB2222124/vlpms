import React from "react";
import {Button, Modal} from "react-bootstrap";

interface Props {
    show: boolean; //Should modal be visible.
    close: () => void; //Function to close modal.
}

/**
 * Utility notification modal used to provide a login prompt message.
 */
function LoginRequiredComponent(props: Props) {

    const show: boolean = props.show;
    const close: () => void = props.close;

    return (
        <Modal show={show} onHide={close}>
            <Modal.Header closeButton>
                <Modal.Title>Login Required</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p>Login to perform this action.</p>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary" onClick={close}>Ok</Button>
            </Modal.Footer>
        </Modal>
    )
}

export default LoginRequiredComponent;