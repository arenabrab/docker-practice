import {makeStyles} from "@material-ui/core/styles";
import IconButton from '@material-ui/core/IconButton';
import './App.css'
import Button from "@material-ui/core/Button";
import SearchIcon from "@material-ui/icons/Search";
import FavoriteIcon from "@material-ui/icons/FavoriteBorder";
import CartIcon from "@material-ui/icons/ShoppingCartOutlined";
import Fab from "@material-ui/core/Fab";
import Person from '@material-ui/icons/PersonOutlined';
import ButtonGroup from "@material-ui/core/ButtonGroup";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import React from "react";

const useStyles = makeStyles((theme) => ({
    root: {
        // flexGrow: 1,
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    toolbar: {
        backgroundColor: '#DECBC1',
        maxHeight: '8vh',
    },
    title: {
        color: '#B13C3C',
        fontWeight: 700,
        margin: theme.spacing('3%', '10%'),
    },
    button: {
        fontWeight: 500,
        fontSize: 17,
        padding: theme.spacing(4),
    },
    fab: {
        backgroundColor: '#B13C3C',
        color: '#C3CBCD',
        '&:hover': {
            backgroundColor: '#923030',
        },
    }
}));

export default function AdAppBar() {
    const classes = useStyles();
    return(
        <div className={'app-bar'} style={{textAlign: 'center'}}>
            <AppBar position="sticky"  elevation={0}>
                <Toolbar variant="dense" className={classes.toolbar}>

                </Toolbar>
            </AppBar>
        </div>
    )

}