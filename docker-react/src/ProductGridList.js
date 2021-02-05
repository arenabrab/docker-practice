import React, {useEffect} from 'react';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import api from "./api";
import ListSubheader from "@material-ui/core/ListSubheader";
import ProductCard from "./ProductCard";
import {makeStyles} from "@material-ui/core/styles";
import Fab from "@material-ui/core/Fab";
import AddIcon from "@material-ui/icons/Add";

const useStyles = makeStyles((theme) => ({
    root: {
        backgroundColor: theme.palette.background.paper,
        width: 500,
        position: 'relative',
        minHeight: 200,
    },
    fab: {
        position: 'absolute',
        bottom: theme.spacing(2),
        right: theme.spacing(2),
    },
}));

export default function ProductGridList({category}) {
    const classes = useStyles();
    const {cat} = category;
    const [items, setItems] = React.useState([]);

    useEffect(() => {
        async function fetchData() {
            const resp = await api.post("sorted" + category, {"direction": "ASC", "property": ["sku"]});
            setItems(resp.data);
        }
        fetchData().then(r => console.log("Done: " + r));
    }, [])


    return (
            <GridList cols={5} cellHeight={'auto'} className={'grid-list'} variant={'scrollable'}>
                <GridListTile cols={5} key={'subheader'}  >
                    <ListSubheader component="div">{cat}</ListSubheader>
                </GridListTile>
                {items.map((item) => (
                    <GridListTile key={item.id} className={'grid-list-tile'}>
                        <ProductCard item={item}/>
                    </GridListTile>
                ))}
            </GridList>

    );
}
