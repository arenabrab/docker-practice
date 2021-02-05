import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import './App.css'
import Button from "@material-ui/core/Button";
import SearchIcon from "@material-ui/icons/Search";
import FavoriteIcon from "@material-ui/icons/FavoriteBorder";
import CartIcon from "@material-ui/icons/ShoppingCartOutlined";
import Fab from "@material-ui/core/Fab";
import Person from '@material-ui/icons/PersonOutlined';
import ButtonGroup from "@material-ui/core/ButtonGroup";

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

export default function DenseAppBar() {
    const classes = useStyles();

    return (
        <div className={'app-bar'} style={{textAlign: 'center'}}>
            <AppBar position="sticky"  elevation={0}>
                <Toolbar variant="dense" className={classes.toolbar}>
                    <Typography className={classes.title} variant="h5" noWrap>
                        MY:LAN
                    </Typography>
                        <Button className={classes.button}>Men</Button>
                        <Button className={classes.button}>Women</Button>
                        <Button className={classes.button}>Home</Button>
                        <Button className={classes.button}>Gift</Button>
                        <Button className={classes.button}>Clearance</Button>


                    <IconButton aria-label="search" style={{marginLeft: '10%'}}>
                        <SearchIcon />
                    </IconButton>
                    <IconButton aria-label="search" >
                        <FavoriteIcon />
                    </IconButton>
                    <IconButton aria-label="search" >
                        <CartIcon />
                    </IconButton>
                    <IconButton>
                        <Fab className={classes.fab}>
                            <Person style={{fontSize: 32}}/>
                        </Fab>
                    </IconButton>

                </Toolbar>
            </AppBar>
        </div>
    );
}
