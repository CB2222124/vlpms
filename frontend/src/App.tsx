import {Route, Routes} from "react-router-dom";
import NavbarComponent from "./components/navigation/NavbarComponent";
import HomePageComponent from "./components/pages/HomePageComponent";
import SearchPageComponent from "./components/pages/SearchPageComponent";
import UserContextComponent from "./components/user/UserContextComponent";
import RegistrationsPageComponent from "./components/pages/RegistrationsPageComponent";
import WishlistPageComponent from "./components/pages/WishlistPageComponent";

/**
 * Root application component. Provides application routes with a navbar above them,
 * wrapped in user context (See UserContextComponent).
 */
function App() {

    return (
        <UserContextComponent>
            <NavbarComponent/>
            <Routes>
                <Route path={"/"} element={<HomePageComponent/>}/>
                <Route path={"search"} element={<SearchPageComponent/>}/>
                <Route path={"registrations"} element={<RegistrationsPageComponent/>}></Route>
                <Route path={"wishlist"} element={<WishlistPageComponent/>}></Route>
            </Routes>
        </UserContextComponent>
    );
}

export default App;