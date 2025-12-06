; ===============================================
; 🚀 FPLScraper — One-click standalone launcher
; ===============================================

#define MyAppName "FPL Report Generator"
#define MyAppVer "2025.12"
#define AppPublisher "Serhii M"
#define MyIcon "dist\fpl-report-generator\fpl-report-generator.ico"
#define MyOutputDir "D:\fpl-report-out"

[Setup]
AppName={#MyAppName}
AppVersion={#MyAppVer}
AppPublisher={#AppPublisher}
DefaultDirName={tmp}\fpl-scraper
ShowLanguageDialog=no
PrivilegesRequired=lowest
Compression=lzma2
SolidCompression=yes
OutputBaseFilename=fpl-report-generator-standalone
OutputDir={#MyOutputDir}
SetupIconFile={#MyIcon}

[Files]
Source: "dist\fpl-report-generator\*"; DestDir: "{app}"; Flags: recursesubdirs createallsubdirs

[Code]
procedure CurStepChanged(CurStep: TSetupStep);
var
  ResultCode: Integer;
begin
  if CurStep = ssPostInstall then begin
    Exec(ExpandConstant('{app}\fpl-report-generator.exe'),
         '--output=' + ExpandConstant('{#MyOutputDir}'),
         ExpandConstant('{app}'),
         SW_SHOWNORMAL,
         ewWaitUntilTerminated,
         ResultCode);

    DelTree(ExpandConstant('{app}'), True, True, True);
  end;
end;
