import React from "react";
import {Button, Modal} from "react-bootstrap";
import {Link} from "react-router-dom";

interface Props {
    show: boolean; //Should the result modal be visible.
    close: () => void; //Closes the modal.
    registration: string; //Listed registration of interest.
    message: string; //Display message.
}

/**
 * Utility modal component for displaying a wishlist message about a specific listing.
 */
function PurchaseResultModalComponent(props: Props) {

    const show: boolean = props.show;
    const close: () => void = props.close;
    const registration: string = props.registration;
    const message: string = props.message;

    return (
        <Modal show={show} onHide={close}>
            <Modal.Header closeButton>
                <Modal.Title>Purchase Registration ({registration})</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p>{message} You can view your owned registrations at any time <Link to={"/registrations"}>here.</Link>
                </p>
                <Button variant="primary" onClick={close}>Ok</Button>
            </Modal.Body>
        </Modal>
    )
}

export default PurchaseResultModalComponent;