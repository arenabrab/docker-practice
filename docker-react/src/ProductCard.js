import React from 'react';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import './App.css'
import Checkbox from "@material-ui/core/Checkbox";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import CardHeader from "@material-ui/core/CardHeader";

export default function ProductCard({item}) {

    return (
        <Card className={'product-card'} elevation={3}>
            <CardHeader
            action={
                <FormControlLabel
                    style={{textAlign:'right'}}
                    control={<Checkbox />}
                    label={'Remove'}/>
            }/>
            <CardActionArea>
                <CardMedia
                    component="img"
                    alt={item.name}
                    image={`data:image/jpeg;base64,${item.image.image.data}`}
                    title={item.name}
                    style={{width: '50%', maxWidth:'50%', height: '50%', display: 'flex', marginLeft: 'auto', marginRight: 'auto'}}/>
                <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                        Name: {item.name}
                    </Typography>
                    <Typography variant="body2" color="textSecondary" component="p">
                        {/*Price: ${item.price}<br/>*/}
                        {/*Employee Price: ${item.employeePrice}<br/>*/}
                        {/*Cut Type: {item.cutType}<br/>*/}
                        {/*Carat Weight: {item.caratWeight}*/}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions>
                <Button size="small" color="primary">
                    Quick View
                </Button>
            </CardActions>
        </Card>
    );
}