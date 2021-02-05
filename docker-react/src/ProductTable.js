import Paper from "@material-ui/core/Paper";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import TablePagination from "@material-ui/core/TablePagination";
import * as React from "react";
import {Component} from "react";
import api from "./api";
import './App.css'

const useStyles = {
    root: {
        width: '100%'
    },
    container: {
        'max-height': 175
    }
};

const columns = {
    products:
        [{id: 'name', label: 'Necklace Name', minWidth: 100},
        {id: 'sku', label: 'SKU', minWidth: 75},
        {id: 'price', label: 'Price', minWidth: 75},
        {id: 'employeePrice', label: 'Employee Price', minWidth: 100},
        {id: 'productMaterial', label: 'Product Material', minWidth: 120},
        {id: 'pageViews', label: 'Page Views', minWidth: 75}],
    rings:
        [{id: 'bandWidth', label: 'Band Width', minWidth: 75},
        {id: 'numberOfStones', label: 'Number of Stones', minWidth: 100},
        {id: 'ringSizeSmallest', label: 'Min Ring Size', minWidth: 75},
        {id: 'ringSizeLargest', label: 'Max Ring Size', minWidth: 75}],
    necklaces:
        [{id: 'claspType', label: 'Clasp Type', minWidth: 75},
        {id: 'chainType', label: 'Chain Type', minWidth: 100},
        {id: 'chainLength', label: 'Chain Length', minWidth: 75}],
    earrings:
        [{id: 'style', label: 'Style', minWidth: 75}],
    gems: [
        {id: 'cutType', label: 'Cut Type', minWidth: 75},
        {id: 'caratWeight', label: 'Carat Weight', minWidth: 75},
        {id: 'cut', label: 'Cut', minWidth: 75},
        {id: 'color', label: 'Color', minWidth: 75},
        {id: 'clarity', label: 'Clarity', minWidth: 75},
        {id: 'certification', label: 'Certification', minWidth: 75}]
};

export default class ProductTable extends Component {
    state = {
        items: [],
        isLoading: false,
        classes: useStyles,
        page: 0,
        rowsPerPage: 25,
        align: 'right',
        columnList: columns.products.concat(columns[this.props.valueFromParent.toLowerCase()]),
    }

    renderTable(params, handleChangePage, handleChangeRowsPerPage) {
        return (
            <Paper className={'table-paper'} elevation={5}>
                <TableContainer className={'table-container'}>
                    <Table stickyHeader >
                        <TableHead>
                            <TableRow>
                                {params.columnList.map((column) => (
                                    <TableCell key={column.id} style={{minWidth: column.minWidth}} align={params.align}>
                                        {column.label}
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody className={params.classes.root}>
                            {params.items.slice(params.page * params.rowsPerPage, params.page * params.rowsPerPage + params.rowsPerPage).map((item) => {
                                return (
                                    <TableRow hover role={"checkbox"} tabIndex={-1} key={item.id}>
                                        {params.columnList.map((column) => {
                                            const value = item[column.id];
                                            return (
                                                <TableCell key={column.id} align={params.align}>
                                                    {value}
                                                </TableCell>
                                            )
                                        })}
                                    </TableRow>
                                )
                            })}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[25, 25, 50]}
                    component={"div"}
                    count={params.items.length}
                    rowsPerPage={params.rowsPerPage}
                    page={params.page}
                    onChangePage={handleChangePage}
                    onChangeRowsPerPage={handleChangeRowsPerPage}
                />
            </Paper>
        )
    }

    componentDidMount() {
        this.setState({isLoading: true})

        api.post('sorted'+this.props.valueFromParent, {"direction": "ASC", "property": ["name"]})
            .then(response => {
                const items = response.data;
                this.setState({items: items, isLoading: false});
            })
    }

    render() {

        const params = this.state;

        if(params.isLoading) {
            return <p>Loading...</p>
        }

        const handleChangePage = (event, newPage) => {
            this.setState({page:newPage});
        };

        const handleChangeRowsPerPage = (event) => {
            this.setState({rowsPerPage: +event.target.value, page:0})
        };

        return (
            <div className={"App"}>
                {this.renderTable(params, handleChangePage, handleChangeRowsPerPage)}
            </div>
        );
    }
}