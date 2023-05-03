import React, {useContext} from "react";
import {UserContext} from "../user/UserContextComponent";
import {Container} from "react-bootstrap";

function WishlistPageComponent() {

    const userContext = useContext(UserContext);
    if (!userContext?.user) return (<Container><span><i>Login to view your wishlist.</i></span></Container>);

    return (
        <>
            <span>We made it</span>
        </>
    )
}

export default WishlistPageComponent;