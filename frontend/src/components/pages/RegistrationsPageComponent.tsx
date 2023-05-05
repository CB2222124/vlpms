import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../user/UserContextComponent";
import {Container, Table} from "react-bootstrap";
import axios from "axios";
import RegistrationComponent from "../registrations/RegistrationComponent";

/**
 * Interface used to represent a registration response.
 */
export interface UserRegistration {
    registration: string;
    style: string;
}

/**
 * User owned registrations page. Conditional rendering based on if the user is logged in. Queries backend and presents
 * owned registrations in tabular form and delegates any additional functionality to its children
 * (See RegistrationComponent).
 */
function RegistrationsPageComponent() {

    const userContext = useContext(UserContext);
    const [registrations, setRegistrations] = useState<UserRegistration[]>([]);

    useEffect(() => {
        if (userContext?.user?.id) findRegistrations(userContext.user.id);
    }, [userContext]);

    //Let's prompt the user to login when appropriate.
    if (!userContext?.user) return (<Container><span><i>Login to view your registrations.</i></span></Container>);

    /**
     * Queries backend service for registrations owned by a specific customer. In a deployed application this
     * request would be validated using something like a JWT.
     * @param id The customer ID to query.
     */
    const findRegistrations = (id: number) => {
        axios.get(`/customer/${id}/registrations`)
            .then(response => {
                setRegistrations(response.data.map((registration: any) => {
                    return {
                        registration: registration.registration,
                        style: registration.style,
                    }
                }));
            })
            .catch(error => console.log(error))
    }

    return (
        <Table striped hover style={{verticalAlign: "middle"}}>
            <thead>
            <tr>
                <th>Registration</th>
                <th>Style</th>
                <th>Check Transfer Eligibility</th>
            </tr>
            </thead>
            <tbody>
            {registrations.map((registration: UserRegistration, index) => {
                return (<tr key={index}><RegistrationComponent registration={registration}/></tr>);
            })}
            </tbody>
        </Table>
    )
}

export default RegistrationsPageComponent;