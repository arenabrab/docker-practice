import React from 'react';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import TabPanel from "./TabPanel";
import ProductGridList from "./ProductGridList";
import AddIcon from '@material-ui/icons/Add';
import EditIcon from '@material-ui/icons/Edit';
import {makeStyles, useTheme} from "@material-ui/core/styles";
import Zoom from "@material-ui/core/Zoom";
import Fab from "@material-ui/core/Fab";
import AddForm from "./AddForm";
import Input from "@material-ui/core/Input";
import Button from "@material-ui/core/Button";

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

export default function Category() {

    const classes = useStyles();
    const theme = useTheme();
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const handleFab = () => {
        return(<AddForm />);
    };

    const fabs = [
        {
            color: 'primary',
            className: classes.fab,
            icon: <AddForm />,
            label: 'Add',
        },
        // {
        //     color: 'secondary',
        //     className: classes.fab,
        //     icon: <EditIcon />,
        //     label: 'Edit',
        // },
    ];

    const transitionDuration = {
        enter: theme.transitions.duration.enteringScreen,
        exit: theme.transitions.duration.leavingScreen,
    };

    return (
        <div className={'classes-root'}>
            <Tabs
                orientation={'vertical'}
                variant={'scrollable'}
                value={value}
                onChange={handleChange}
                textColor={"primary"}
                indicatorColor={"primary"}
                className={'classes-tabs'}>

                <Tab label="Rings" />
                <Tab label="Necklaces" />
                <Tab label="Earrings" />
                <Tab label="Gems" />

            </Tabs>
            <TabPanel value={value} index={0}>
                <ProductGridList category={'Rings'}/>
            </TabPanel>
            <TabPanel value={value} index={1}>
                <ProductGridList category={'Necklaces'}/>
            </TabPanel>
            <TabPanel value={value} index={2}>
                <ProductGridList category={'Earrings'}/>
            </TabPanel>
            <TabPanel value={value} index={3}>
                <ProductGridList category={'Gems'}/>
            </TabPanel>
            {fabs.map((fab, index) => (
                <Zoom
                    key={fab.color}
                    in={value === index}
                    timeout={transitionDuration} refsdfsdfssdffds
                    style={{
                        transitionDelay: `${value === index ? transitionDuration.exit : 0}ms`,
                    }}
                    unmountOnExit>

                    <Fab className={fab.className} color={fab.color} size={'large'} onClick={handleFab}>
                        {fab.icon}
                    </Fab>
                </Zoom>
            ))}
         </div>
    );
}