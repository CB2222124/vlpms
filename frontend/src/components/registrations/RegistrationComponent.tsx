import React, {SyntheticEvent, useState} from "react";
import {Button, Form} from "react-bootstrap";
import axios from "axios";
import {UserRegistration} from "../pages/RegistrationsPageComponent";

interface Props {
    registration: UserRegistration; //User owned registration.
}

/**
 * Component responsible for a row in the users owned registrations table. Provides functionality to check
 * if a registration can be applied to a specific vehicle by calling the backend service.
 */
function RegistrationComponent(props: Props) {
    const registration = props.registration;

    const [searchRegistration, setSearchRegistration] = useState<string>("");

    const [message, setMessage] = useState<string>("Enter a vehicle registration to check if you can transfer this registration.");

    const handleChange = (event: SyntheticEvent) => {
        const target = event.target as HTMLInputElement;
        setSearchRegistration(target.value);
    }

    /**
     * Calls the backend service to check if this registration is transferable to a specified vehicle and adjusts
     * information text based on the response.
     * @param event Form submit event.
     */
    function checkTransferable(event: any) {
        event.preventDefault();
        axios.get(`/registration/transferable?registration=${registration.registration}&targetRegistration=${searchRegistration}`)
            .then(response => {
                if (response.data.transferable) {
                    setMessage(`Great news! ${registration.registration} is eligible for transfer to the vehicle associated with ${searchRegistration}.`);
                } else {
                    setMessage(`${registration.registration} is not eligible for transfer to the vehicle associated with ${searchRegistration} (Registration cannot make a vehicle appear younger than it is).`);
                }
            })
            .catch(error => {
                if (error.response.status == 404) {
                    setMessage(`No details are held for the vehicle registration ${searchRegistration}.`);
                } else {
                    setMessage(`Unfortunately the vehicle transfer eligibility service is not available at this time.`);
                }
            })
    }

    return (
        <>
            <td className="registrations__column registration"
                style={{fontSize: "24px"}}>{registration.registration}</td>
            <td className="registrations__column">{registration.style}</td>
            <td>
                <Form className="d-flex" onSubmit={checkTransferable}>
                    <Form.Control type="search" placeholder="Target Vehicle Registration"
                                  value={searchRegistration}
                                  onChange={handleChange} className="registrations__column me-2"
                                  required
                                  pattern="(^[A-Z]{2}[0-9]{2}\s?[A-Z]{3}$)|(^[A-Z][0-9]{1,3}[A-Z]{3}$)|(^[A-Z]{3}[0-9]{1,3}[A-Z]$)"/>
                    <Button type="submit" className="me-2">Search</Button>
                    <Form.Text>{message}</Form.Text>
                </Form>

            </td>
        </>
    )
}

export default RegistrationComponent;