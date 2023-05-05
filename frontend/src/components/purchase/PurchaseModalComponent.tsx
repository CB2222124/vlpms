import React, {useContext, useState} from "react";
import {Button, Col, Form, InputGroup, Modal, Row, Table} from "react-bootstrap";
import {SearchResultData} from "../pages/SearchPageComponent";
import axios from "axios";
import {UserContext} from "../user/UserContextComponent";
import PurchaseResultModalComponent from "./PurchaseResultModalComponent";

interface Props {
    show: boolean; //If the modal should be visible.
    close: () => void; //Function for closing the modal.
    data: SearchResultData; //Individual listing data.
}

/**
 * Modal component used to validate user payment information and send a request to the backend for
 * registration assignment. This component assumes its parent has ensured user context is available.
 */
function PurchaseModalComponent(props: Props) {

    const userContext = useContext(UserContext);
    const show: boolean = props.show;
    const close: () => void = props.close;
    const data: SearchResultData = props.data;


    const [showResult, setShowResult] = useState(false);
    const [resultMessage, setResultMessage] = useState("");

    function closeResult() {
        setShowResult(false);
    }

    /**
     * Makes a call to the backend service requesting for the customer to be assigned a registration.
     * @param event Form submit event.
     */
    function handlePurchase(event: any) {
        event.preventDefault();
        axios.post(`/registration/assignListing`, {
            customerId: userContext?.user?.id,
            registration: data.registration.registration
        })
            .then(() => {
                close();
                setResultMessage(`Purchase successful!`)
                setShowResult(true);
            })
            .catch((error) => {
                if (error.response.status == 404) {
                    close();
                    setResultMessage(`Registration no longer available.`)
                    setShowResult(true);
                } else {
                    close();
                    setResultMessage(`Internal server error.`)
                    setShowResult(true);
                }
            })
    }

    return (
        <>
            <Modal show={show} onHide={close}>
                <Modal.Header closeButton>
                    <Modal.Title>Purchase Registration ({data.registration.registration})</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handlePurchase}>
                        <Form.Group className="mb-3">
                            <Form.Label>Card Owner</Form.Label>
                            <Form.Control type="text" placeholder="Card Owner Name"
                                          required
                                          pattern="^[a-zA-Z ]*$"
                                          maxLength={24}/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Card Number</Form.Label>
                            <InputGroup className="mb-3">
                                <Form.Control type="text" placeholder="0000 0000 0000 0000"
                                              required
                                              pattern="[0-9 ]+"
                                              minLength={15}
                                              maxLength={19}/>
                                <InputGroup.Text id="basic-addon2"><i
                                    className="fa fa-credit-card me-2"/>Visa</InputGroup.Text>
                            </InputGroup>
                        </Form.Group>
                        <Row>
                            <Form.Group as={Col}>
                                <Form.Label>Expiration date</Form.Label>
                                <InputGroup className="mb-3">
                                    <Form.Control type="text" placeholder="MM"
                                                  required
                                                  pattern="^1[0-2]|0[1-9]$"
                                                  minLength={2}
                                                  maxLength={2}/>
                                    <Form.Control type="text" placeholder="YY"
                                                  required
                                                  pattern="^[0-9]*$"
                                                  minLength={2}
                                                  maxLength={2}/>
                                </InputGroup>
                            </Form.Group>
                            <Form.Group as={Col}>
                                <Form.Label>CVV</Form.Label>
                                <Form.Control type="text" placeholder="000"
                                              required
                                              pattern="^[0-9]*$"
                                              minLength={3}
                                              maxLength={3}/>
                            </Form.Group>
                        </Row>
                        <Form.Group className="mb-3">
                            <Form.Label>Summary</Form.Label>
                            <Table striped bordered>
                                <tbody>
                                <tr>
                                    <td>Registration ({data.registration.registration})</td>
                                    <td>£{data.pricePence / 100}</td>
                                </tr>
                                <tr>
                                    <td>DVLA Fee</td>
                                    <td>£80</td>
                                </tr>
                                <tr>
                                    <td>Total Due</td>
                                    <td>£{data.pricePence / 100 + 80}</td>
                                </tr>
                                </tbody>
                            </Table>
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            <i className="fa fa-lock me-2"/>Submit
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>
            <PurchaseResultModalComponent show={showResult} close={closeResult}
                                          registration={data.registration.registration} message={resultMessage}/>
        </>
    )
}

export default PurchaseModalComponent;