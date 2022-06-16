/*
 * Copyright (C) 2022 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import BuildingInput from "./BuildingInput";
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    result: any[],
    dark: boolean
}

class App extends Component<{}, AppState> {

    constructor(props: any) {
        super(props);
        this.state = {
            result: [],
            dark: false
        }
    }

    switchDark() {
        if (!this.state.dark) { // an easy method that helps to change the appearance of the UI
            this.setState({
                dark: true
            })
            // @ts-ignore
            document.querySelector("body").classList.add("night");
            // @ts-ignore
            document.querySelector("h1").classList.add("font-night");
            // @ts-ignore
            document.querySelector("h2").classList.add("font-night");
            // @ts-ignore
            document.querySelector("#add-button").classList.add("font-night");
            // @ts-ignore
            document.querySelector("#switch-button").classList.add("font-night");
            document.querySelectorAll("button").forEach((element) => {
                // @ts-ignore
                element.classList.remove("day");
                element.classList.add("night");
            });
            document.querySelectorAll("label").forEach((element) => {
                // @ts-ignore
                element.classList.remove("font-day");
                element.classList.add("font-night");
            });
            document.querySelectorAll("select").forEach((element) => {
                // @ts-ignore
                element.classList.remove("day");
                element.classList.add("night");
                element.classList.remove("font-day");
                element.classList.add("font-night");
            });
        } else {
            this.setState({
                dark: false
            })
            // @ts-ignore
            document.querySelector("body").classList.remove("night");
            // @ts-ignore
            document.querySelector("h1").classList.remove("font-night");
            // @ts-ignore
            document.querySelector("h2").classList.remove("font-night");
            // @ts-ignore
            document.querySelector("#add-button").classList.remove("font-night");
            // @ts-ignore
            document.querySelector("#switch-button").classList.remove("font-night");
            document.querySelectorAll("button").forEach((element) => {
                // @ts-ignore
                element.classList.add("day");
                element.classList.remove("night");
            });
            document.querySelectorAll("label").forEach((element) => {
                // @ts-ignore
                element.classList.add("font-day");
                element.classList.remove("font-night");
            });
            document.querySelectorAll("select").forEach((element) => {
                // @ts-ignore
                element.classList.add("day");
                element.classList.remove("night");
                element.classList.add("font-day");
                element.classList.remove("font-night");
            });
        }
    }

    render() {
        return (
            <div id="main">
                <h1 id="app-title">Campus Map Path Finder!</h1>
                <div id="content">
                    <div>
                        <Map result={this.state.result} dark={this.state.dark}/>
                    </div>
                    <div>
                        <BuildingInput onChange={(value) => {
                            this.setState({
                                result: value
                            })
                        }} dark={this.state.dark}/>
                    </div>
                </div>
                <button onClick={() => {this.switchDark();}} className="day" id="switch-button">Switch Dark Mode</button>
            </div>
        );
    }
}

export default App;
