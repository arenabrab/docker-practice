import './App.css';
import * as React from 'react';
import InitPaper from "./InitPaper";
import Category from "./Category";

export default class App extends React.Component {
  render() {
    return (
        <div className={"App"} >
            {/*<InitPaper className={"app-paper"} />*/}
            <Category />
        </div>
    );
  }
}
