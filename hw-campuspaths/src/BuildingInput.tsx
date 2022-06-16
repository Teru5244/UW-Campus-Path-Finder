import React, {Component} from 'react';

interface BuildingInputProps {
    onChange(result:any[]): void,
    dark: boolean
}

interface BuildingInputState {
    inputs: any,
    index: number,
    draw: string[][]
}

class BuildingInput extends Component<BuildingInputProps, BuildingInputState> {

    constructor(props: BuildingInputProps) {
        super(props);
        this.state = {
            inputs: [], // an array that stores all the existing input boxes
            index: 0, // index for the unique key of each input box
            draw: [] // draw state uses string[][] that will have an array of the parameters of start and end buildings
                     // to send to server, a single request will be like[START, END, COLOR].
        }
    }

    sendRequest = async () => { // send request to the server
        try {
            let result: any = [];
            let curr = this.state.draw;
            for (let i = 0; i < curr.length; i++) {

                let responsePromise = fetch("http://localhost:4567/find-path?start=" + curr[i][0] + "&end=" + curr[i][1]);
                let response = await responsePromise;
                if (!response.ok) {
                    alert("The status is wrong! Expected: 200, Was: " + response.status);
                    return;
                }
                let promise = response.json();
                let object = await promise;

                if (object !== null) {
                    // add color as one of the field in the object
                    object.color = curr[i][2];
                }
                // return the result of fetch as an array of objects
                result.push(object);
            }
            this.props.onChange(result);
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }

    selectChange(event: any, i: number, j: number) {
        let curr = this.state.draw;
        curr[i][j] = event.target.value;
        this.setState({
            draw: curr
        })
    }

    addOnePath() { // will have three select box that each represents the start building, end building, and the color of the path
                   // a button at the end will be able to delete the path from the list
        let currInputs: any = this.state.inputs;
        let currDraw: string[][] = this.state.draw;
        let i = this.state.index;
        currInputs.push(
            <div key={"input" + i} id={"input" + i} className="input-box">
                <label className={(this.props.dark) ? "font-night" : "font-day"}>
                    Start:
                    <select name="start" onChange={(event: any) => this.selectChange(event, i, 0)} className={(this.props.dark) ? "night font-night" : "day font-day"}>
                        <option value="CSE">Paul G. Allen Center for Computer Science & Engineering</option>
                        <option value="BAG">Bagley Hall (East Entrance)</option>
                        <option value="BAG (NE)">Bagley Hall (Northeast Entrance)</option>
                        <option value="BGR">By George</option>
                        <option value="CS2">Bill & Melinda Gates Center For Computer Science & Engineering</option>
                        <option value="DEN">Denny Hall</option>
                        <option value="EEB">Electrical Engineering Building (North Entrance)</option>
                        <option value="EEB (S)">Electrical Engineering Building (South Entrance)</option>
                        <option value="GWN">Gowen Hall</option>
                        <option value="KNE">Kane Hall (North Entrance)</option>
                        <option value="KNE (E)">Kane Hall (East Entrance)</option>
                        <option value="KNE (SE)">Kane Hall (Southeast Entrance)</option>
                        <option value="KNE (S)">Kane Hall (South Entrance)</option>
                        <option value="KNE (SW)">Kane Hall (Southwest Entrance)</option>
                        <option value="LOW">Loew Hall</option>
                        <option value="MGH">Mary Gates Hall (North Entrance)</option>
                        <option value="MGH (E)">Mary Gates Hall (East Entrance)</option>
                        <option value="MGH (S)">Mary Gates Hall (South Entrance)</option>
                        <option value="MGH (SW)">Mary Gates Hall (Southwest Entrance)</option>
                        <option value="MLR">Miller Hall</option>
                        <option value="MOR">Moore Hall</option>
                        <option value="MUS">Music Building (Northwest Entrance)</option>
                        <option value="MUS (E)">Music Building (East Entrance)</option>
                        <option value="MUS (SW)">Music Building (Southwest Entrance)</option>
                        <option value="MUS (S)">Music Building (South Entrance)</option>
                        <option value="OUG">Odegaard Undergraduate Library</option>
                        <option value="PAA">Physics/Astronomy Building A</option>
                        <option value="PAB">Physics/Astronomy Building</option>
                        <option value="SAV">Savery Hall</option>
                        <option value="SUZ">Suzzallo Library</option>
                        <option value="T65">Thai 65</option>
                        <option value="FSH">Fishery Sciences Building</option>
                        <option value="MCC">McCarty Hall (Main Entrance)</option>
                        <option value="MCC (S)">McCarty Hall (South Entrance)</option>
                        <option value="UBS">University Bookstore</option>
                        <option value="UBS (Secret)">University Bookstore (Secret Entrance)</option>
                        <option value="RAI">Raitt Hall (West Entrance)</option>
                        <option value="RAI (E)">Raitt Hall (East Entrance)</option>
                        <option value="ROB">Roberts Hall</option>
                        <option value="CHL">Chemistry Library (West Entrance)</option>
                        <option value="CHL (NE)">Chemistry Library (Northeast Entrance)</option>
                        <option value="CHL (SE)">Chemistry Library (Southeast Entrance)</option>
                        <option value="IMA">Intramural Activities Building</option>
                        <option value="HUB">Student Union Building (Main Entrance)</option>
                        <option value="HUB (West Food)">Student Union Building (West Food Entrance)</option>
                        <option value="HUB (South Food)">Student Union Building (South Food Entrance)</option>
                        <option value="MNY">Meany Hall (Northeast Entrance)</option>
                        <option value="MNY (NW)">Meany Hall (Northwest Entrance)</option>
                        <option value="PAR">Parrington Hall</option>
                        <option value="MCM">McMahon Hall (Northwest Entrance)</option>
                        <option value="MCM (SW)">McMahon Hall (Southwest Entrance)</option>
                        <option value="CMU">Communications Building</option>
                    </select>
                </label>
                <label className={(this.props.dark) ? "font-night" : "font-day"}>
                    End:
                    <select name="end" onChange={(event: any) => this.selectChange(event, i, 1)} className={(this.props.dark) ? "night font-night" : "day font-day"}>
                        <option value="CS2">Bill & Melinda Gates Center For Computer Science & Engineering</option>
                        <option value="CSE">Paul G. Allen Center for Computer Science & Engineering</option>
                        <option value="BAG">Bagley Hall (East Entrance)</option>
                        <option value="BAG (NE)">Bagley Hall (Northeast Entrance)</option>
                        <option value="BGR">By George</option>
                        <option value="DEN">Denny Hall</option>
                        <option value="EEB">Electrical Engineering Building (North Entrance)</option>
                        <option value="EEB (S)">Electrical Engineering Building (South Entrance)</option>
                        <option value="GWN">Gowen Hall</option>
                        <option value="KNE">Kane Hall (North Entrance)</option>
                        <option value="KNE (E)">Kane Hall (East Entrance)</option>
                        <option value="KNE (SE)">Kane Hall (Southeast Entrance)</option>
                        <option value="KNE (S)">Kane Hall (South Entrance)</option>
                        <option value="KNE (SW)">Kane Hall (Southwest Entrance)</option>
                        <option value="LOW">Loew Hall</option>
                        <option value="MGH">Mary Gates Hall (North Entrance)</option>
                        <option value="MGH (E)">Mary Gates Hall (East Entrance)</option>
                        <option value="MGH (S)">Mary Gates Hall (South Entrance)</option>
                        <option value="MGH (SW)">Mary Gates Hall (Southwest Entrance)</option>
                        <option value="MLR">Miller Hall</option>
                        <option value="MOR">Moore Hall</option>
                        <option value="MUS">Music Building (Northwest Entrance)</option>
                        <option value="MUS (E)">Music Building (East Entrance)</option>
                        <option value="MUS (SW)">Music Building (Southwest Entrance)</option>
                        <option value="MUS (S)">Music Building (South Entrance)</option>
                        <option value="OUG">Odegaard Undergraduate Library</option>
                        <option value="PAA">Physics/Astronomy Building A</option>
                        <option value="PAB">Physics/Astronomy Building</option>
                        <option value="SAV">Savery Hall</option>
                        <option value="SUZ">Suzzallo Library</option>
                        <option value="T65">Thai 65</option>
                        <option value="FSH">Fishery Sciences Building</option>
                        <option value="MCC">McCarty Hall (Main Entrance)</option>
                        <option value="MCC (S)">McCarty Hall (South Entrance)</option>
                        <option value="UBS">University Bookstore</option>
                        <option value="UBS (Secret)">University Bookstore (Secret Entrance)</option>
                        <option value="RAI">Raitt Hall (West Entrance)</option>
                        <option value="RAI (E)">Raitt Hall (East Entrance)</option>
                        <option value="ROB">Roberts Hall</option>
                        <option value="CHL">Chemistry Library (West Entrance)</option>
                        <option value="CHL (NE)">Chemistry Library (Northeast Entrance)</option>
                        <option value="CHL (SE)">Chemistry Library (Southeast Entrance)</option>
                        <option value="IMA">Intramural Activities Building</option>
                        <option value="HUB">Student Union Building (Main Entrance)</option>
                        <option value="HUB (West Food)">Student Union Building (West Food Entrance)</option>
                        <option value="HUB (South Food)">Student Union Building (South Food Entrance)</option>
                        <option value="MNY">Meany Hall (Northeast Entrance)</option>
                        <option value="MNY (NW)">Meany Hall (Northwest Entrance)</option>
                        <option value="PAR">Parrington Hall</option>
                        <option value="MCM">McMahon Hall (Northwest Entrance)</option>
                        <option value="MCM (SW)">McMahon Hall (Southwest Entrance)</option>
                        <option value="CMU">Communications Building</option>
                    </select>
                </label>
                <label className={(this.props.dark) ? "font-night" : "font-day"}>
                    Color:
                    <select name="color" onChange={(event: any) => this.selectChange(event, i, 2)} className={(this.props.dark) ? "night font-night" : "day font-day"}>
                        <option value="red">Red</option>
                        <option value="blue">Blue</option>
                        <option value="yellow">Yellow</option>
                        <option value="green">Green</option>
                        <option value="orange">Orange</option>
                        <option value="cyan">Cyan</option>
                        <option value="magenta">Magenta</option>
                        <option value="purple">Purple</option>
                        <option value="white">White</option>
                        <option value="black">Black</option>
                        <option value="gray">Gray</option>
                        <option value="pink">Pink</option>
                        <option value="silver">Silver</option>
                        <option value="Brown">Brown</option>
                        <option value="maroon">Maroon</option>
                        <option value="tan">Tan</option>
                        <option value="lime">Lime</option>
                        <option value="olive">Olive</option>
                        <option value="turquoise">Turquoise</option>
                        <option value="indigo">Indigo</option>
                        <option value="teal">Teal</option>
                        <option value="violet">Violet</option>
                    </select>
                </label>
                <button onClick={() => {this.delete(i)}} className={(this.props.dark) ? "night" : "day"}>Delete</button>
            </div>
        );

        // store the default values into the draw state
        currDraw.push(["CSE", "CS2", "red"]);
        this.setState({
            inputs: currInputs,
            index: i + 1,
            draw: currDraw
        })
    }

    delete(i: number) {
        let currInputs = this.state.inputs;
        let currDraw = this.state.draw;
        currInputs = currInputs.filter((element: any) => {
            return element.props.id !== "input" + i;
        })
        currDraw[i] = ["", "", ""]; // only omit the values instead of the whole array
        this.setState({
            inputs: currInputs,
            draw: currDraw
        });
    }


    render() {
        // add all the input boxes to the render()
        let result: any = [];
        let currInputs = this.state.inputs;
        currInputs.forEach((element: any) => {
            result.push(element);
        })

        return (
            <div id="inputs">
                <h2>Control Panel</h2>
                {result}
                <button className="day" onClick={() => {this.addOnePath()}} id="add-button">
                    Add a path
                </button>
                <button className="day" onClick={() => {this.sendRequest().then()}} id="draw-button">
                    Draw!
                </button>
                <button className="day" id="clear-button" onClick={() => {
                    this.props.onChange([]);
                    this.setState({
                       inputs: [],
                       index: 0
                    });
                }}>
                    Clear
                </button>
            </div>
        );
    }
}

export default BuildingInput;