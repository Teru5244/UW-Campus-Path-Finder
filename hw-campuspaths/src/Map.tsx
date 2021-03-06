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

import { LatLngExpression } from "leaflet";
import React, { Component } from "react";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import { UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER } from "./Constants";

// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

// NOTE: This component is a suggestion for you to use, if you would like to. If
// you don't want to use this component, you're free to delete it or replace it
// with your hw-lines Map

interface MapProps {
  // TODO: Define the props of this component.
    result: any[],
    dark: boolean
}

interface MapState {}

class Map extends Component<MapProps, MapState> {

  render() {
        let props = this.props.result;

        let result: any = [];

        // loop over the array of object to draw the lines
        for (let i = 0; i < props.length; i++) {
            if (props[i] !== null && props[i].path !== undefined) {
                let path: any[] = props[i].path;

                let j: number = 0;
                path.forEach((segment) => {
                    let start_x = segment.start.x;
                    let start_y = segment.start.y;
                    let end_x = segment.end.x;
                    let end_y = segment.end.y;

                    result.push(
                        <MapLine key={"edge" + i + "-" + j} color={props[i].color} x1={start_x} y1={start_y} x2={end_x} y2={end_y}/>
                    );
                    j += 1;
                })
            }
        }

    return (
      <div id="map">
        <MapContainer
          center={position}
          zoom={16}
          scrollWheelZoom={false}
        >
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          {
            // TODO: Render map lines here using the MapLine component. E.g.
            // <MapLine key="key1" color="red" x1={1000} y1={1000} x2={2000} y2={2000}/>
            // will draw a red line from the point 1000,1000 to 2000,2000 on the
            // map. Note that key should be a unique key that only this MapLine has.
              result
          }
        </MapContainer>
      </div>
    );
  }
}

export default Map;
