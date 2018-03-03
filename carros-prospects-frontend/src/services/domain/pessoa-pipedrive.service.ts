import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { API_CONFIG } from "../../config/api.config";
import { PessoaPipedriveDTO } from "../../models/pessoa-pipedrive.dto";
import { Observable } from "rxjs";

@Injectable()
export class PessoaPipedriveService {

    constructor(private http: HttpClient) {
    }

    findAll(token : string) : Observable<PessoaPipedriveDTO[]> {
        return this.http.get<PessoaPipedriveDTO[]>(`${API_CONFIG.baseUrl}/pessoas?token=${token}`);
    }

}