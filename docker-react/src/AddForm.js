import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import clsx from "clsx";
import Button from "@material-ui/core/Button";
import Drawer from "@material-ui/core/Drawer";
import Select from "@material-ui/core/Select";
import Input from "@material-ui/core/Input";
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";
import Checkbox from "@material-ui/core/Checkbox";
import ListItemText from "@material-ui/core/ListItemText";
import FormHelperText from "@material-ui/core/FormHelperText";
import api from "./api";
import Fab from "@material-ui/core/Fab";
import Zoom from "@material-ui/core/Zoom";
import AddIcon from "@material-ui/icons/Add";

const useStyles = makeStyles((theme) => ({
    list: {
        width: 250,
    },
    fullList: {
        width: 'auto',
    },
    formControl: {
        margin: theme.spacing(1,0,0,0),
        minWidth: 197,
        maxWidth: 300,
    },
    addImage: {
        margin: theme.spacing(1,0,0,0)
    }
}));

const materials = [
    "GOLD",
    "SILVER",
    "WHITE_GOLD",
    "RUBBER"
];

export default function AddForm({}) {

    const classes = useStyles();
    const [state, setState] = React.useState({
        right: false,
    });
    const [image, setImage] = React.useState();
    const [formData, setFormData] = React.useState({
        name:"",
        sku:0,
        price:"",
        employeePrice:"",
        productMaterial:[]
    });

    const handleFormData = (prop, newVal) => {
        setFormData({...formData, [prop]: newVal});
    }

    const handleImage = (img) => {
        setImage(img);
    }

    const toggleDrawer = (anchor, open) => (event) => {
        if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
        }
        setState({ ...state, [anchor]: open });
    };

    const handleSubmit = async () => {
        let postForm = new FormData();
        postForm.append('json', JSON.stringify(formData));
        if(image) {
            postForm.append('image', image);
        }
        await api.post("rings", postForm);
    };

    const list = (anchor) => (
        <div
            className={clsx(classes.list, {
                [classes.fullList]: anchor === 'top' || anchor === 'bottom',
            })}
            role="presentation"
            style={{textAlign: 'center'}}>

            Add a thing

            <form onSubmit={handleSubmit} id={'json'}>
                <FormControl className={classes.formControl}>
                    <Input id={'name'} onChange={e => handleFormData('name', e.target.value)}/>
                    <FormHelperText>Name</FormHelperText>
                </FormControl>
                <FormControl className={classes.formControl}>
                    <Input id={'sku'} onChange={e => handleFormData('sku', e.target.value)}/>
                    <FormHelperText>SKU</FormHelperText>
                </FormControl>
                <FormControl className={classes.formControl}>
                    <Input id={'price'} onChange={e => handleFormData('price', e.target.value)}/>
                    <FormHelperText>Price</FormHelperText>
                </FormControl>
                <FormControl className={classes.formControl}>
                    <Input id={'employeePrice'} onChange={e => handleFormData('employeePrice', e.target.value)}/>
                    <FormHelperText>Employee Price</FormHelperText>
                </FormControl>
            <FormControl className={classes.formControl}>
                <Select
                    id="productMaterial"
                    multiple
                    value={formData.productMaterial}
                    onChange={e => handleFormData('productMaterial', e.target.value)}
                    input={<Input />}
                    renderValue={(selected) => selected.join(', ')}>
                    {materials.map((material) => (
                        <MenuItem key={material} value={material}>
                            <Checkbox checked={formData.productMaterial.indexOf(material) > -1} />
                            <ListItemText primary={material} />
                        </MenuItem>
                    ))}
                </Select>
                <FormHelperText>Product Material</FormHelperText>
            </FormControl>
            <FormControl className={classes.formControl}>
                <Button
                    className={classes.addImage}
                    variant="contained"
                    component="label"
                    color={'primary'}>
                    Add Image
                    <input name={'image'} type="file" accept={'image/*'} hidden onChange={e => handleImage(e.target.files[0])}/>
                </Button>
            </FormControl>
            <FormControl className={classes.formControl}>
                <Button variant={'contained'} color={'secondary'} type={'submit'}>Add</Button>
            </FormControl>
            </form>
        </div>
    );

    return(
        <div>
            {[''].map((anchor) => (
                <React.Fragment key={anchor}>
                    <Fab className={'fab'} color={'primary'} size={'large'} onClick={toggleDrawer(anchor, true)}>
                        {<AddIcon />}
                    </Fab>
                    {/*<Button onClick={toggleDrawer(anchor, true)}>{anchor}</Button>*/}
                    <Drawer anchor={'right'} open={state[anchor]} onClose={toggleDrawer(anchor, false)}>
                        {list(anchor)}
                    </Drawer>
                </React.Fragment>
            ))}
        </div>
    );
}