; ===============================================
; 🚀 FPLScraper — One-click standalone launcher
; ===============================================

#define MyAppName "FPL Scraper"
#define MyAppVer "2025.11"
#define AppPublisher "Serhii M"
#define MyIcon "dist\fpl-scraper\fpl-scraper.ico"
#define MyOutputDir "D:\fpl-scraper-out"

[Setup]
AppName={#MyAppName}
AppVersion={#MyAppVer}
AppPublisher={#AppPublisher}
DefaultDirName={tmp}\fpl-scraper
ShowLanguageDialog=no
PrivilegesRequired=lowest
Compression=lzma2
SolidCompression=yes
OutputBaseFilename=fpl-scraper-standalone
OutputDir={#MyOutputDir}
SetupIconFile={#MyIcon}

[Files]
Source: "dist\fpl-scraper\*"; DestDir: "{app}"; Flags: recursesubdirs createallsubdirs

[Code]
procedure CurStepChanged(CurStep: TSetupStep);
var
  ResultCode: Integer;
begin
  if CurStep = ssPostInstall then begin
    Exec(ExpandConstant('{app}\fpl-scraper.exe'),
         '--output=' + ExpandConstant('{#MyOutputDir}'),
         ExpandConstant('{app}'),
         SW_SHOWNORMAL,
         ewWaitUntilTerminated,
         ResultCode);

    DelTree(ExpandConstant('{app}'), True, True, True);
  end;
end;
