import React, {useContext, useState} from "react";
import {SearchResultData} from "../pages/SearchPageComponent";
import LoginRequiredComponent from "../user/LoginRequiredComponent";
import PurchaseModalComponent from "../purchase/PurchaseModalComponent";
import {UserContext} from "../user/UserContextComponent";
import WishlistModalComponent from "../wishlist/WishlistModalComponent";
import axios from "axios";

interface Props {
    searchResultData: SearchResultData; //Listing.
}

/**
 * Component responsible for displaying a single listing, with functionality to wishlist or purchase the listing.
 * Actions dependent on user state are checked and a prompt to login is provided if necessary.
 */
function SearchResultComponent(props: Props) {
    const searchResultData = props.searchResultData;
    const userContext = useContext(UserContext);

    //Action requires login modal.
    const [showLoginPrompt, setShowLoginPrompt] = useState(false);
    const openLoginPrompt = () => setShowLoginPrompt(true);
    const closeLoginPrompt = () => setShowLoginPrompt(false);

    //Purchase modal.
    const [showPurchase, setShowPurchase] = useState(false);
    const openPurchase = () => setShowPurchase(true);
    const closePurchase = () => setShowPurchase(false);

    //Wishlist modal.
    const [showWishlist, setShowWishlist] = useState(false);
    const [wishlistMessage, setWishlistMessage] = useState("");

    /**
     * Makes a call to the backend service to add a specific listing to the users wishlist and provides a success
     * or failure modal depending on service availability as well as if the listing was already wishlisted.
     */
    const openWishlist = () => {
        axios.post(`/customer/wishlist`, {
            customerId: userContext?.user?.id,
            registration: searchResultData.registration.registration
        })
            .then(() => setWishlistMessage(searchResultData.registration.registration + " has been added to your wishlist!"))
            .catch((error) => {
                if (error.response.status == 400) {
                    setWishlistMessage(searchResultData.registration.registration + " is already in your wishlist!")
                } else {
                    setWishlistMessage(searchResultData.registration.registration + " is no longer available.")
                }
            })
        setShowWishlist(true);
    };
    const closeWishlist = () => setShowWishlist(false);

    return (
        <>
            <div className="search__result">
                <span><b>{"£" + searchResultData.pricePence / 100}</b></span>
                <button className="float-end" onClick={userContext?.user ? openWishlist : openLoginPrompt}>
                    <i className="fa fa-heart-circle-plus text-primary"/>
                </button>
                <p className="mb-0 registration">{searchResultData.registration.registration}</p>
                <button className="text-primary" onClick={userContext?.user ? openPurchase : openLoginPrompt}>
                    <i className="fa fa-shopping-cart"></i> Purchase
                </button>
                <p className="float-end text-muted small"><b>{searchResultData.dateListed}</b></p>
            </div>
            <LoginRequiredComponent show={showLoginPrompt} close={closeLoginPrompt}/>
            <PurchaseModalComponent show={showPurchase} close={closePurchase} data={searchResultData}/>
            <WishlistModalComponent show={showWishlist} close={closeWishlist}
                                    registration={searchResultData.registration.registration}
                                    message={wishlistMessage}/>
        </>
    )
}

export default SearchResultComponent;