import { TestBed, inject } from '@angular/core/testing';

import { AppServiceService } from './app-service.service';

describe('AppServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AppServiceService]
    });
  });

  it('should be created', inject([AppServiceService], (service: AppServiceService) => {
    expect(service).toBeTruthy();
  }));
});
