/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package run_my_self;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlanControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private run_my_self.PlanRepository planRepository;

    @Test
    public void postPlanOK() throws Exception {

        try {
            JSONObject reqBody = new JSONObject();
            reqBody.put("name", "planName");
            reqBody.put("date", "20180413");
            this.mockMvc.perform(post("/plans").content(reqBody.toString()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                    andDo(print()).andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.name").value("planName"))
                    .andExpect(jsonPath("$.data.status").value("Open"));
        } finally {
            planRepository.deleteAll();
        }
    }

    @Test
    public void getPlanOK() throws Exception {

        try {
            JSONObject reqBody = new JSONObject();
            reqBody.put("name", "planName");
            reqBody.put("date", "20180413");
            for (int i = 0; i < 3; i++) {
                this.mockMvc.perform(post("/plans").content(reqBody.toString()).
                        contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                        andExpect(status().isOk());
            }
            reqBody = new JSONObject();
            reqBody.put("name", "planName");
            reqBody.put("date", "20180414");
            for (int i = 0; i < 2; i++) {
                this.mockMvc.perform(post("/plans").content(reqBody.toString()).
                        contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                        andExpect(status().isOk());
            }
            this.mockMvc.perform(get("/plans")).andExpect(status().isOk()).
                    andExpect(jsonPath("$.data").isArray()).
                    andExpect(jsonPath("$.data", hasSize(5)));
            this.mockMvc.perform(get("/plans?date=20180414")).andExpect(status().isOk()).
                    andExpect(jsonPath("$.data").isArray()).
                    andExpect(jsonPath("$.data", hasSize(2)));
            this.mockMvc.perform(get("/plans?date=20180413")).andExpect(status().isOk()).
                    andExpect(jsonPath("$.data").isArray()).
                    andExpect(jsonPath("$.data", hasSize(3)));

        } finally {
            planRepository.deleteAll();
        }
    }

    @Test
    public void putPlanOK() throws Exception {

        try {
            JSONObject reqBody = new JSONObject();
            reqBody.put("name", "planName");
            reqBody.put("date", "20180413");
            reqBody.put("status","Done");
            this.mockMvc.perform(put("/plans/2").content(reqBody.toString()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                    andDo(print()).andExpect(status().isOk()).
                    andExpect(jsonPath("$.data.id").value(2));
        } finally {
            planRepository.deleteAll();
        }
    }

    @Test
    public void deletePlanOK() throws Exception {

        try {
            JSONObject reqBody = new JSONObject();
            reqBody.put("name", "planName");
            reqBody.put("date", "20180413");
            this.mockMvc.perform(post("/plans").content(reqBody.toString()).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                    andDo(print()).andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").value(1))
                    .andExpect(jsonPath("$.data.name").value("planName"))
                    .andExpect(jsonPath("$.data.status").value("Open"));
            this.mockMvc.perform(delete("/plans/1")).andDo(print()).andExpect(status().isOk());
        } finally {
            planRepository.deleteAll();
        }
    }

}
