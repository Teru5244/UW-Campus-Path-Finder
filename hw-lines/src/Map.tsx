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

interface MapProps {
  mapEdges: string[][]
}

interface MapState {}

class Map extends Component<MapProps, MapState> {
  render() {
    let result: any[] = [];
    let i: number = 1;
    this.props.mapEdges.forEach((edge) => {
      let canAdd: boolean = true;
      for (let j = 0; j < 4; j++) {
        if (isNaN(Number(edge[j])) || Number(edge[j]) > 4000 || Number(edge[j]) < 0) {
          canAdd = false;
          alert("Invalid argument: " + edge[j] + " on line " + i + ".");
        }
      }
      if (canAdd) {
        result.push(
            <MapLine key={"e" + i} color={edge[4]} x1={Number(edge[0])} y1={Number(edge[1])} x2={Number(edge[2])}
                     y2={Number(edge[3])}/>
        );
        i++;
      }
    })


    return (
      <div id="map">
        <MapContainer
          center={position}
          zoom={15}
          scrollWheelZoom={false}
        >
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          {result}
        </MapContainer>
      </div>
    );
  }
}

export default Map;
