import React from 'react';
import Paper from '@material-ui/core/Paper';
import DenseAppBar from "./AppBar";
import AdAppBar from "./AdAppBar";

export default function SimplePaper() {

    return (
        <div>
            <DenseAppBar />
            <Paper className={'app-paper'} elevation={0}/>
            <AdAppBar />
        </div>

    );
}