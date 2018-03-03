import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { API_CONFIG } from "../../config/api.config";
import { CarroDTO } from "../../models/carro.dto";
import { Observable } from "rxjs";

@Injectable()
export class CarroService {

    constructor(private http: HttpClient) {
    }

    save(carro : CarroDTO) {
        return this.http.post(
            `${API_CONFIG.baseUrl}/carros`, 
            carro,
            { 
                observe: 'response', 
                responseType: 'text'
            }
        ); 
    }

    update(carro : CarroDTO, id : number) {
        return this.http.put(
            `${API_CONFIG.baseUrl}/carros/${id}`, 
            carro,
            { 
                observe: 'response', 
                responseType: 'text'
            }
        ); 
    }

    delete(id : number) {
        return this.http.delete(
            `${API_CONFIG.baseUrl}/carros/${id}`, 
            { 
                observe: 'response', 
                responseType: 'text'
            }
        ); 
    }

    findAll() : Observable<CarroDTO[]> {
        return this.http.get<CarroDTO[]>(`${API_CONFIG.baseUrl}/carros`);
    }

    findCores() : Observable<string[]> {
        return this.http.get<string[]>(`${API_CONFIG.baseUrl}/carros/cores`);
    }

}