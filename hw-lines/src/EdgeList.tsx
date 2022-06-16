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

interface EdgeListProps {
    onChange(edges: string): void;  // called when a new edge list is ready
                                 // TODO: once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
}

interface  EdgeListState {
    textValue: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            textValue: ""
        }
    }

    textChange(event: any) {
    this.setState({
        textValue: event.target.value
    })}

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={(event: any) => this.textChange(event)}
                    value={this.state.textValue}
                /> <br/>
                <button onClick={() => {this.props.onChange(this.state.textValue);}}>Draw</button>
                <button onClick={() => {this.props.onChange("");}}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
