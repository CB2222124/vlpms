import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "../user/UserContextComponent";
import {Container} from "react-bootstrap";
import axios from "axios";

interface UserRegistration {
    registration: string;
    style: string;
}

function RegistrationsPageComponent() {

    const [registrations, setRegistrations] = useState<UserRegistration[]>([]);

    const userContext = useContext(UserContext);

    useEffect(() => {
        if(userContext?.user?.id) findRegistrations(userContext.user.id);
    }, [userContext]);

    if (!userContext?.user) return (<Container><span><i>Login to view owned registrations.</i></span></Container>);

    const findRegistrations = (id: number) => {
        axios.get(`http://localhost:8080/customer/${id}/registrations`)
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
        <>
            {registrations.map((registration: UserRegistration, index) => {
                return (<div key = {index}><p>{registration.registration}</p></div>);
            })}
        </>
    )
}

export default RegistrationsPageComponent;