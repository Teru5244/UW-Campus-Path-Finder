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

import React, { Component } from "react";
import EdgeList from "./EdgeList";
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    edges: string[][];
}

class App extends Component<{}, AppState> { // <- {} means no props.

  constructor(props: any) {
    super(props);
    this.state = {
        edges: []
    };
  }

  render() {
    return (
      <div>
        <h1 id="app-title">Line Mapper!</h1>
        <div>
          {}
          <Map mapEdges={this.state.edges}/>
        </div>
        <EdgeList
          onChange={(value) => {
            let edgeList: string[][] = [];
            if (value !== "") {
                const edgeStringListByLine = value.split("\n");
                let i: number = 1;
                edgeStringListByLine.forEach(function (line) {
                    line = line.trim();
                    const edgeStrings = line.split(" ");
                    if (edgeStrings.length !== 5) {
                        alert("Invalid number of arguments on line " + i + ".");
                    } else {
                        edgeList.push(edgeStrings);
                    }
                    i++;
                });
            }
            this.setState({
                edges: edgeList
            });
          }}
        />
      </div>
    );
  }
}

export default App;
