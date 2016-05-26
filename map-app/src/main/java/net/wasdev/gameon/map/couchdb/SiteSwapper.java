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
package net.wasdev.gameon.map.couchdb;

import java.util.Collection;

import net.wasdev.gameon.map.couchdb.auth.ResourceAccessPolicy;
import net.wasdev.gameon.map.models.Site;

/*
* This interface is being implemented by MapRepository
* It is used for authentication. Any user that has a ResourceAccessPolicy that includes this 
* class has permission to swap sites.
*/
public interface SiteSwapper {
    
    public Collection<Site> swapRooms(ResourceAccessPolicy accessPolicy, String user, String id1, String id2);
}