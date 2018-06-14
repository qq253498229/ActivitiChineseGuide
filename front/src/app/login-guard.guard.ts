import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {AppServiceService} from './app-service.service';

@Injectable()
export class LoginGuardGuard implements CanActivate {

  constructor(
    public service: AppServiceService,
    public router: Router
  ) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (!this.service.getUser()) {
      this.router.navigate(['login']);
    }
    return true;
  }
}
