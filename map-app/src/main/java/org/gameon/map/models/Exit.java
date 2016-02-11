/*******************************************************************************
 * Copyright (c) 2016 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.gameon.map.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(
        description = "An exit provides the door and URL required for the player to traverse the path "
                + "to the next room.")
@JsonInclude(Include.NON_EMPTY)
public class Exit {

    /** Room id */
    private String id;

    /** Terse/Short room name */
    private String name;

    /** full name */
    private String fullName;

    /** description of target room's door */
    private String door = null;

    /** target room connection details */
    private ConnectionDetails connDetails = null;

    public Exit() {}

    public Exit(Site targetSite, String direction) {
        this.id = targetSite.getId();

        if ( targetSite.getInfo() != null ) {
            this.name = targetSite.getInfo().getName();
            this.fullName = targetSite.getInfo().getFullName();
            this.connDetails = targetSite.getInfo().getConnectionDetails();

            setDoorNameFromTargetSite(targetSite, direction);

            // This won't be the prettiest. ew.
            if ( this.fullName == null )
                this.fullName = this.name;

        } else {
            // Empty/placeholder room. Still navigable if very unclear.
            this.name = "Nether space";
            this.fullName = "Nether space";
            this.door = "Tenuous doorway filled with gray fog";
        }
    }

    private void setDoorNameFromTargetSite(Site targetSite, String direction) {
        RoomInfo targetSiteInfo = targetSite.getInfo();
        Doors doors = targetSiteInfo != null ? targetSiteInfo.getDoors() : null;
        if (doors != null) {
            // Note the direction flip. Assume we're in a room,
            // and there is a room to the North:
            // To build the North _EXIT_ (of the South room), we're
            // getting the South _DOOR_ (of the North room).
            switch(direction) {
                case "N" :
                    this.door = doors.getS();
                    break;
                case "S" :
                    this.door = doors.getN();
                    break;
                case "E" :
                    this.door = doors.getW();
                    break;
                case "W" :
                    this.door = doors.getE();
                    break;
            }
        }

        // Really generic. They gave us nothing interesting.
        if ( this.door == null )
            this.door = "A door";
    }

    @ApiModelProperty(
            value = "Id of the target room",
            name = "_id",
            readOnly = true,
            example = "2",
            required = true)
    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ApiModelProperty(
            value = "Short/Terse name of the target room",
            example = "Barn",
            required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ApiModelProperty(
            value = "Human-friendly room name",
            example = "The First Room",
            required = false)
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @ApiModelProperty(
            value = "Player-friendly description of the door (provided by the target room or generated by the map)",
            example = "Big red barn door",
            required = true)
    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    @ApiModelProperty(
            required = true)
    public ConnectionDetails getConnectionDetails() {
        return connDetails;
    }
    public void setConnection(ConnectionDetails connection) {
        this.connDetails = connection;
    }


    @Override
    public String toString()  {
      StringBuilder sb = new StringBuilder();
      sb.append("class Exit {\n");
      sb.append("  id: ").append(id).append("\n");
      sb.append("  name: ").append(name).append("\n");
      sb.append("  door: ").append(door).append("\n");
      sb.append("  connDetails: ").append(connDetails).append("\n");
      sb.append("}\n");
      return sb.toString();
    }
}
