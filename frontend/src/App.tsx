import {Navigate, Route, Routes} from "react-router-dom";
import NavbarComponent from "./components/navigation/NavbarComponent";
import AddVehicleComponent from "./components/vehicles/AddVehicleComponent";
import EditVehicleComponent from "./components/vehicles/EditVehicleComponent";
import VehicleListComponent from "./components/vehicles/VehicleListComponent";
import ViewVehicleComponent from "./components/vehicles/ViewVehicleComponent";
import HomeComponent from "./components/HomeComponent";

function App() {
    return (
        <>
            <NavbarComponent/>
            <Routes>
                <Route path={'/'} element={<HomeComponent/>}/>
                <Route path={'/vehicles/list'} element={<VehicleListComponent/>}/>
                <Route path={'/vehicles/add'} element={<AddVehicleComponent/>}/>
                <Route path={'/vehicles/view/:vehicleId'} element={<ViewVehicleComponent/>}/>
                <Route path={'/vehicles/edit/:vehicleId'} element={<EditVehicleComponent/>}/>
            </Routes>
        </>
    );
}
export default App;