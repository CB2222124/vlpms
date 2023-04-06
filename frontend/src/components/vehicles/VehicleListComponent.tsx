import React from 'react';
import {Link} from 'react-router-dom';

function VehicleListComponent() {
    return (
        <>
            <section className='vehicle-search'>
                <div className='container'>
                    <div className='grid'>
                        <div className='row'>
                            <div className='col'>
                                <p className='h3'>
                                    Vehicle Manager
                                    <Link to={'/vehicles/add'} className='btn btn-primary ms-2'>
                                        <i className='fa fa-plus-circle me-2'/>
                                        Add
                                    </Link>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default VehicleListComponent;