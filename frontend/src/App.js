import './App.css';

import React, { useState, useEffect, useRef } from 'react';
import { Toast } from 'primereact/toast';

import DataTable from 'react-data-table-component';

import 'primereact/resources/themes/fluent-light/theme.css';
import 'primereact/resources/primereact.min.css';

function App() {
    const [cities, setCities] = useState([]);
    const [nameFilter, setNameFilter] = useState("");
    const [page, setPage] = useState(1);
    const [totalRecords, setTotalRecords] = useState(0);
    const [perPage, setPerPage] = useState(10);
    const [loading, setLoading] = useState(false);

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
                    alt='City'
                />
            )
        }
    ];

    const fetchCities = async (perPage, page) => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                page: page - 1,
                size: perPage,
                sort: "name",
                name: nameFilter
            })
        };

        setLoading(true);

        fetch("http://localhost:8080/api/city/search", requestOptions)
            .then(res => res.json())
            .then((data) => {
                    setCities(data.content);
                    setTotalRecords(data.totalElements);
                    setLoading(false);
                },
                (error) => {
                }
            ).then(data => {
            setLoading(false );
        })
    };

    const handlePageChange = page => {
        fetchCities(perPage, page);
        setPage(page);
    };

    const handlePerRowsChange = (newPerPage, page) => {
        fetchCities(newPerPage, page);
        setPage(page);
        setPerPage(newPerPage);
    };

    const handleFilterApply = () => {
        fetchCities(perPage, page);
    };

    useEffect(() => {
        fetchCities(perPage, 1);

    }, []);

    return (
        <div className="App">
          <header className="App-header">
              <div id="header-holder">
                <h1>City List</h1>
              </div>
          </header>
            <content>
                <div id="content-holder">
                    <div class="filter-block">
                        <label>
                            <input name="nameFilter"
                                 value={nameFilter}
                                 onChange={e => setNameFilter(e.target.value)} />

                            <button onClick={handleFilterApply}>
                                Find city by name
                            </button>
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
            </content>

        </div>
    );
}

export default App;
