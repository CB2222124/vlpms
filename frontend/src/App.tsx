import {Route, Routes} from "react-router-dom";
import NavbarComponent from "./components/navigation/NavbarComponent";
import HomePageComponent from "./components/pages/HomePageComponent";
import SearchPageComponent from "./components/pages/SearchPageComponent";

function App() {
    return (
        <>
            <NavbarComponent/>
            <Routes>
                <Route path={"/"} element={<HomePageComponent/>}/>
                <Route path={"search"} element={<SearchPageComponent/>}/>
            </Routes>
        </>
    );
}

export default App;