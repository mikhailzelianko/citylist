import './App.css';

import React, { useState, useEffect, useRef } from 'react';
import Button from '@mui/material/Button';
import DialogTitle from '@mui/material/DialogTitle';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import Dialog from '@mui/material/Dialog';
import TextField from '@mui/material/TextField';
import { Toast } from 'primereact/toast';
import {DebounceInput} from 'react-debounce-input';

import DataTable from 'react-data-table-component';

import 'primereact/resources/themes/fluent-light/theme.css';
import 'primereact/resources/primereact.min.css';

function App() {
    const [cities, setCities] = useState([]);
    const [cityId, setCityId] = useState([]);
    const [cityName, setCityName] = useState([]);
    const [cityPictureUrl, setCityPictureUrl] = useState([]);
    const [nameFilter, setNameFilter] = useState("");
    const [page, setPage] = useState(1);
    const [totalRecords, setTotalRecords] = useState(0);
    const [perPage, setPerPage] = useState(10);
    const [loading, setLoading] = useState(false);
    const [displayDialog, setDisplayDialog] = useState(false);

    const toast = useRef(null);

    const columns = [
        {
            name: 'Id',
            selector: row => row.id,
        },
        {
            name: 'Name',
            selector: row => row.name,
        },
        {
            name: 'Picture',
            cell: row => (
                <img
                    src={row.pictureUrl}
                    width={60}
                    onError={(e) => e.target.src='https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg?20200913095930'}
                    alt={row.name}
                />
            )
        },
        {
            name: 'Actions',
                cell: row => (
                    <button onClick={() => handleClickOpen(row)}>
                        Edit
                    </button>
            )
        }
    ];

    const fetchCities = async (perPage, page) => {

        const response = await fetch(`http://localhost:8080/api/v1/city/?page=${page - 1}&size=${perPage}&name=${nameFilter}`);
        return await response.json();
    }

    const loadCities = async (perPage, page) => {
        setLoading(true);
        const data = await fetchCities(perPage, page);

        setCities(data.cities);
        setTotalRecords(data.totalElements);
        setLoading(false);
    };

    const saveCity = async () => {
        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "name": cityName,
                "pictureUrl": cityPictureUrl
            })
        };

        const response = await fetch(`http://localhost:8080/api/v1/city/${cityId}`, requestOptions);

        if (!response.ok) {
            const data = await response.json();
            toast.current.show({ severity: 'error', summary: 'Error', detail: data.messages.join(", "), life: 5000 });
        } else {
            toast.current.show({
                severity: 'success',
                summary: '',
                detail: "City has been saved successfully",
                life: 2000
            });
        }

        loadCities(perPage, page);
        setDisplayDialog(false);
    };

    const handlePageChange = page => {
        loadCities(perPage, page);
        setPage(page);
    };

    const handlePerRowsChange = (newPerPage, page) => {
        loadCities(newPerPage, page);
        setPage(page);
        setPerPage(newPerPage);
    };

    const handleFilterApply = () => {
        loadCities(perPage, page);
    };

    const handleClickOpen = (city) => {
        setCityId(city.id);
        setCityName(city.name);
        setCityPictureUrl(city.pictureUrl);
        setDisplayDialog(true);
    };

    const handleClose = () => {
        setDisplayDialog(false);
    };

    const handleFilterChange = (e) => {
        setNameFilter(e.target.value);
    }

    const handleCityNameChange = (e) => {
        setCityName(e.target.value);
    };

    const handleCityPictureUrlChange = (e) => {
        setCityPictureUrl(e.target.value);
    };

    useEffect(() => {
        loadCities(perPage, 1);
    }, []);

    useEffect(() => {
        handleFilterApply();
    }, [nameFilter]);

    return (
        <div className="App">
            <Toast ref={toast} />
            <header className="App-header">
                <div id="header-holder">
                    City List
                    <span>| app</span>
                </div>
            </header>
            <div id="content">
                <div id="content-holder">
                    <div className="filter-block">
                        <label>
                            <div>Filter Cities by name:</div>
                            <DebounceInput
                                minLength={3}
                                debounceTimeout={300}
                                onChange={handleFilterChange} />

                        </label>
                    </div>
                    {<DataTable
                        columns={columns}
                        data={cities}
                        progressPending={loading}
                        pagination
                        paginationServer
                        paginationTotalRows={totalRecords}
                        onChangePage={handlePageChange}
                        onChangeRowsPerPage={handlePerRowsChange}
                    />}
                </div>
            </div>
            <Dialog open={displayDialog} onClose={handleClose} fullWidth>
                <DialogTitle>Edit City</DialogTitle>
                <DialogContent>
                    <div>
                        <TextField
                            id="city-name"
                            label="City Name"
                            value={cityName}
                            margin="dense"
                            fullWidth
                            onChange={handleCityNameChange}
                        />
                    </div>
                    <div>
                        <TextField
                            id="city-picture"
                            label="City Picture URL"
                            value={cityPictureUrl}
                            margin="dense"
                            fullWidth
                            onChange={handleCityPictureUrlChange}
                        />
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={saveCity} variant="contained">Save</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default App;
